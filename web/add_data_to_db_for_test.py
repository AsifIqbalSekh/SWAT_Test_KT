import json
from pymongo import MongoClient

# Connect to MongoDB
client = MongoClient("mongodb://localhost:27017")
db = client.SWAT_Data
collection = db["EcosystemPOC"]

# Load data from JSON file
with open('ECOSYSTEM_POC_SWAT.json') as f:
    collections = json.load(f)

# Insert documents into the collection
for doc in collections:
    result = collection.insert_one(doc)

print("Inserted document for test")
