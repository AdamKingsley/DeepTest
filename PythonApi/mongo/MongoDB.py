# -*- coding:utf-8 -*-
from pymongo import MongoClient


class MongoDB(object):
    def __init__(self, settings):
        try:
            self.conn = MongoClient(settings["ip"], int(settings["port"]))
        except Exception as e:
            print(e)
        if 'db_name' in settings.keys():
            self.db = self.conn[settings["db_name"]]
            if 'db_user_name' in settings.keys() and 'db_user_pwd' in settings.keys():
                self.db.authenticate(settings['db_user_name'], settings['db_user_pwd'])
        if 'collection_name' in settings.keys():
            self.collection = self.db[settings["collection_name"]]

    def change_db(self, db_name):
        self.db = self.conn[db_name]
        return self.db

    def change_collection(self, collection_name):
        self.collection = self.db[collection_name]
        return self.collection

    def insert(self, dic):
        print("insert...")
        self.collection.insert(dic)

    def insert_many(self, dics):
        print("insert many...")
        self.collection.insert_many(dics)

    def update(self, dic, newdic):
        print("update...")
        self.collection.update(dic, newdic)

    def delete(self, dic):
        print("delete...")
        self.collection.remove(dic)

    def dbfind(self, dic):
        print("find...")
        data = self.collection.find(dic)
