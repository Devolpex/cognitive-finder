db = db.getSiblingDB("patient_db");
db.createCollection("dummy_collection");

db = db.getSiblingDB("notification_db");
db.createCollection("dummy_collection");

