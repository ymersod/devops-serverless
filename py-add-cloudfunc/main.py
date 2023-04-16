import functions_framework

from flask import Flask, request, json

@functions_framework.http
def hello_get(request):
    if (request.is_json):
        data = json.loads(request.data)
        return '{"result":' + str(data["val1"] + data["val2"]) + "}"
    else:
        return "Malformed request"