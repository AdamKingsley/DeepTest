from flask import Flask, jsonify, request, abort

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/custom/paint', methods=['POST'])
def custom_sample():
    if not request.json:
        abort(400)
    JSON = request.json

    return jsonify(request.json), 200


if __name__ == '__main__':
    app.run()
