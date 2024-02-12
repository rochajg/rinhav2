db.createCollection("users")
db.users.createIndex({"userId": 1})
db.users.insertMany([
    {"userId": 2, "name": "Rubyzeiro",     "limit": 80000, "balance": 0, "transactions": []},
    {"userId": 1, "name": "Phpzeiro",      "limit": 100000, "balance": 0, "transactions": []},
    {"userId": 5, "name": "Golangueiro",   "limit": 500000, "balance": 0, "transactions": []},
    {"userId": 3, "name": "Javascriptero", "limit": 1000000, "balance": 0, "transactions": []},
    {"userId": 4, "name": "Javeiro",       "limit": 10000000, "balance": 0, "transactions": []},
])

db.createCollection("transactions")
db.transactions.createIndex({"userId": 1})
