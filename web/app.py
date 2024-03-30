from flask import Flask, jsonify, request
from pymongo import MongoClient
from flask_restful import Api, Resource
import json
from my_logs import log_info,log_error
import jwt
from functools import wraps


app = Flask('EcosystemPOCApi')
api = Api(app)
# JWT secret key
app.config['SECRET_KEY'] = 'swatteam'

# DB Connection & creation

client = MongoClient("mongodb://db:27017")
# client = MongoClient("mongodb://localhost:27017")


db = client.SWAT_Data
collection = db["EcosystemPOC"]


def jwt_required(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        token = request.headers.get('Authorization')
        if not token:
            return jsonify({'message': 'Missing JWT token'})
        try:
            data = jwt.decode(token.split()[1], app.config['SECRET_KEY'], algorithms=["HS256"])
            if data['username'] != 'admin' or data['password'] != 'asif@123':
                return jsonify({'message': 'Invalid username or password'})
        except jwt.ExpiredSignatureError:
            return jsonify({'message': 'Expired JWT token'})
        except jwt.InvalidTokenError:
            return jsonify({'message': 'Invalid JWT token'})
        
        request.username=data['username']
        return func(*args, **kwargs)
    return wrapper


# Function to check for required and default values
def check_fields(json_data):
    required_fields = ['services', 'swat_dept']
    default_fields = ['parent_service', 'asset_architect', 'asset_tpm', 'poc']
    
    missing_fields = [field for field in required_fields if field not in json_data]
    if missing_fields:
        return jsonify({'error': f'Missing required fields: {", ".join(missing_fields)}'}), 400
    
    for field in default_fields:
        if field not in json_data:
            json_data[field] = 'NA'
    
    return True, json_data

# CRUD operations
class POC_Operation(Resource):
    def get(self):
        # Read operation
        data = list(collection.find({}, {'_id': 0}))  # Exclude _id field from response
        log_info(200, f'POC list fetched successfully')
        return data

    def post(self):
        # Create operation
        json_data = request.json
        
        # Check for required and default values
        is_valid, processed_data = check_fields(json_data)
        if is_valid is not True:
            return is_valid
        
        # Check if the service already exists
        existing_service = collection.find_one({'services': json_data['services']})
        if existing_service:
            del existing_service['_id']  # Remove _id field from response
            log_error(400,"User trying to add already existing service")
            return {'message': 'Service already exists', 'service_details': existing_service}, 409
        
        result = collection.insert_one(processed_data)
        return {'message': 'Data inserted successfully', 'id': str(result.inserted_id)}, 201

    def put(self, services):
        # Update operation
        json_data = request.json
        
        # Check for required and default values
        is_valid, processed_data = check_fields(json_data)
        if is_valid is not True:
            return is_valid
        
        # Check if the service exists
        existing_service = collection.find_one({'services': services})
        if not existing_service:
            return {'message': 'Service does not exist'}, 404
        
        query = {'services': services}
        new_values = {'$set': processed_data}
        result = collection.update_one(query, new_values)
        return {'message': 'Data updated successfully', 'modified_count': result.modified_count}

    @jwt_required
    def delete(self, services):
        # Delete operation
        existing_service = collection.find_one({'services': services})
        if not existing_service:
            log_error(400,"User trying to delete the service which does not exists")
            return {'message': 'Service does not exist'}, 404
        
        query = {'services': services}
        result = collection.delete_one(query)
        log_info(200, f'Services: {services} deleted successfully by Username: {request.username}')
        return {'message': 'Data deleted successfully', 'deleted_count': result.deleted_count}

api.add_resource(POC_Operation, '/ecosystempoc', '/ecosystempoc/<string:services>')



# Search operation
@app.route('/ecosystempoc/search/', defaults={'keyword': ''})
@app.route('/ecosystempoc/search/<string:keyword>')
def search(keyword):
    if not keyword:
        return jsonify({'error': 'No keyword provided in URL path'}), 400

    results = list(collection.find({'services': {'$regex': keyword, '$options': 'i'}}, {'_id': 0}))
    if not results:
        log_error(400,f"User trying to Use Search api with services: {keyword} which does not exist")
        return jsonify({'message': 'No documents found for the provided keyword'}), 404
    log_info(400,f"User fetch data successfully using Search api with services: {keyword}")
    return jsonify(results)

# API endpoint to fetch error logs
@app.route('/logs/error')
def get_error_logs():
    error_logs = []
    with open("error_log.json", "r") as error_log_file:
        for line in error_log_file:
            error_logs.append(json.loads(line.strip()))
    return jsonify(error_logs)

# API endpoint to fetch info logs
@app.route('/logs/info')
def get_info_logs():
    info_logs = []
    with open("info_log.json", "r") as info_log_file:
        for line in info_log_file:
            info_logs.append(json.loads(line.strip()))
    return jsonify(info_logs)



# Login endpoint to obtain JWT token
@app.route('/login', methods=['POST'])
def login():
    data = request.json
    try:
        if data['username'] == 'admin' and data['password'] == 'asif@123':
            token = jwt.encode({'username': data['username'], 'password': data['password']}, app.config['SECRET_KEY'], algorithm='HS256')
            # Return the token directly
            return jsonify({'token': token})
        else:
            return jsonify({'error': 'Invalid username or password'}), 401
    except:
        return jsonify({'error': 'Provide username or password'}), 401





@app.route('/test')
def about():
    return jsonify({"Developed By":"Sk Asif Iqbal","status":"success","msg":"application running successfully! I Love You Rahana ! ","purpose":"testing please ignore!"})

if __name__=='__main__':
    app.run(host='0.0.0.0', debug=True)
