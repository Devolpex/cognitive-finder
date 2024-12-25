db = db.getSiblingDB("patient_db");
// Create a user for the patient_db database
db.createUser({
    user: "patient_user",
    pwd: "patient_password",
    roles: [
      {
        role: "readWrite",
        db: "patient_db"
      }
    ]
  });
db.createCollection("dummy_collection");

db = db.getSiblingDB("notification_db");
db.createCollection("dummy_collection");

db = db.getSiblingDB("konga_db");
db.createCollection("dummy_collection");