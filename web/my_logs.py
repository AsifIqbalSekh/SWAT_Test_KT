import datetime
import json


with open("info_log.json", "a") as info_log_file:
    pass

with open("error_log.json", "a") as error_log_file:
    pass

# Function to log info messages
def log_info(status_code, message):
    timestamp = str(datetime.datetime.now())
    log_entry = {"timestamp": timestamp, "status_code": status_code, "message": message}
    with open("info_log.json", "a") as info_log_file:
        json.dump(log_entry, info_log_file)
        info_log_file.write("\n")

# Function to log error messages
def log_error(status_code, message):
    timestamp = str(datetime.datetime.now())
    log_entry = {"timestamp": timestamp, "status_code": status_code, "error_message": message}
    with open("error_log.json", "a") as error_log_file:
        json.dump(log_entry, error_log_file)
        error_log_file.write("\n")