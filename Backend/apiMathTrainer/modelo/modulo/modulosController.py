import os
import json
from moduloDAO import ModuloDAO


def GetAll():
    global status_code, response_body, modulo_dao
    try:
        # Create new instance DAO
        modulo_dao = ModuloDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        response_body = modulo_dao.findAll()
        status_code = 200
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        modulo_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def Post(event):
    global status_code, response_body, modulo_dao
    try:
        # Extract Data from event

        data = json.loads(event['body'])
        Modulo = data

        # Create new instance DAO
        modulo_dao = ModuloDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        moduloId = modulo_dao.save(Modulo)

        response_body = f"Data insert success with: ID {moduloId}"
        status_code = 200
    except KeyError as e:
        response_body = {'message': f'Body mising arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        modulo_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def GetByID(event):
    idModulo = event['pathParameters']['idModulo']
    global status_code, response_body, modulo_dao
    try:

        # Create new instance DAO
        modulo_dao = ModuloDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        response_body = modulo_dao.findById(idModulo)
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        modulo_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response

def Put(event):
    idModulo = event['pathParameters']['idModulo']
    global status_code, response_body, modulo_dao
    try:
        # Extract Data from event
        data = json.loads(event['body'])
        data["idModulo"] =idModulo
        Modulo = data

        # Create new instance DAO
        modulo_dao = ModuloDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        afectedRows = modulo_dao.update(Modulo)
        response_body = f'Rows updated: {afectedRows}'
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        modulo_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def Delete(event):
    idModulo = event['pathParameters']['idModulo']
    global status_code, response_body, modulo_dao
    try:
        # Create new instance DAO
        modulo_dao = ModuloDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        afectedRows = modulo_dao.delete(idModulo)
        response_body = f'Rows deleted: {afectedRows}'
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        modulo_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response
