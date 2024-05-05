import os
import json
from temaDAO import TemaDAO


def GetAll():
    global status_code, response_body, tema_dao
    try:
        # Create new instance DAO
        tema_dao = TemaDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        response_body = tema_dao.findAll()
        status_code = 200
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        tema_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def Post(event):
    global status_code, response_body, tema_dao
    try:
        # Extract Data from event
        tema = json.loads(event['body'])

       # Create new instance DAO
        tema_dao = TemaDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        temaId = tema_dao.save(tema)

        response_body = f"Data insert success with: ID {temaId}"
        status_code = 200
    except KeyError as e:
        response_body = {'message': f'Body mising arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        tema_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def GetByID(event):
    idTema = event['pathParameters']['idTema']
    global status_code, response_body, tema_dao
    try:

        # Create new instance DAO
        tema_dao = TemaDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        response_body = tema_dao.findById(idTema)
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        tema_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response

def Put(event):

    global status_code, response_body, tema_dao
    try:
        # Extract Data from event
        tema = json.loads(event['body'])
        tema['idTema'] = event['pathParameters']['idTema']


        # Create new instance DAO
        tema_dao = TemaDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        afectedRows = tema_dao.update(tema)
        response_body = f'Rows updated: {afectedRows}'
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        tema_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response


def Delete(event):
    idTema = event['pathParameters']['idTema']
    global status_code, response_body, tema_dao
    try:
        # Create new instance DAO
        tema_dao = TemaDAO(os.environ['HOST'], os.environ['USER'], os.environ['PASSWORD'], os.environ['DB'])
        afectedRows = tema_dao.delete(idTema)
        response_body = f'Rows deleted: {afectedRows}'
        status_code = 200

    except KeyError as e:
        response_body = {'message': f' Missing arguments: {e}'}
        status_code = 400
    except Exception as e:
        response_body = {'message': f'Internal Server Error: {e}'}
        status_code = 500
    finally:
        tema_dao.__del__()
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response
