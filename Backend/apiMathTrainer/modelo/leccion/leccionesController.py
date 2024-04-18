import os
import json
from leccionDAO import LeccionDAO


def GetAll(event):
    global status_code, response_body, leccion_dao
    try:
        idModulo = event['pathParameters']['idModulo']
        # Create new instance DAO
        leccion_dao = LeccionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        response_body = leccion_dao.FindAll(idModulo)
        status_code = 200
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        leccion_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def Post(event):
    global status_code, response_body, leccion_dao
    try:
        # Extract Data from event
        leccion = json.loads(event['body'])
        leccion["idModulo"] = event['pathParameters']['idModulo']


        # Create new instance DAO
        leccion_dao = LeccionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        moduloId = leccion_dao.Save(leccion)

        response_body = f"Data insert success with: ID {moduloId}"
        status_code = 200
    except KeyError as e:
        response_body = {'message': f'Body mising arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        leccion_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def GetByID(event):
    idLeccion = event['pathParameters']['idLeccion']
    global status_code, response_body, leccion_dao
    try:

        # Create new instance DAO
        leccion_dao = LeccionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        response_body = leccion_dao.FindById(idLeccion)
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        leccion_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response

def Put(event):
    idLeccion = event['pathParameters']['idLeccion']
    global status_code, response_body, leccion_dao
    try:
        # Extract Data from event
        leccion = json.loads(event['body'])
        leccion['idModulo'] = event['pathParameters']['idModulo']

        # Create new instance DAO
        leccion_dao = LeccionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        afectedRows = leccion_dao.Update(leccion)
        response_body = f'Rows updated: {afectedRows}'
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        leccion_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def Delete(event):
    idLeccion = event['pathParameters']['idLeccion']
    global status_code, response_body, leccion_dao
    try:
        # Create new instance DAO
        leccion_dao = LeccionDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        afectedRows = leccion_dao.Delete(idLeccion)
        response_body = f'Rows deleted: {afectedRows}'
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        leccion_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response
