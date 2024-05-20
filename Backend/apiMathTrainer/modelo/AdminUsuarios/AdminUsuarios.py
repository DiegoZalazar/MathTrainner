import os
import json
import boto3


def getUsersGroup(group_name, user_pool_id):
    # Inicializar el cliente de Cognito
    global status_code, response_body
    client = boto3.client('cognito-idp')

    try:
        # Obtener los usuarios del grupo especificado
        response = client.list_users_in_group(
            UserPoolId=user_pool_id,
            GroupName=group_name
        )
        usersJson = []

        # Extraer información relevante de los usuarios
        users = []
        for user in response['Users']:
            user_data = {}
            for attribute in user["Attributes"]:
                user_data[attribute["Name"]] = attribute["Value"]
            usersJson.append(user_data)

        status_code = 200
        response_body = usersJson


    except Exception as e:
        status_code = 500
        response_body = {'message': f'Internal Server Error: {e}'}
    finally:

        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response

def deleteUser(event, user_pool_id):
    # Inicializar el cliente de Cognito
    global status_code, response_body, user_id
    client = boto3.client('cognito-idp')

    try:
        data = json.loads(event['body'])
        user_id = data['user_id']

        response_body = client.admin_delete_user(
            UserPoolId=user_pool_id,
            Username=user_id
        )
        status_code = 200

    except KeyError as e:
        response_body = {'message': f'Body mising arguments: {e}'}
    except client.exceptions.UserNotFoundException:
        status_code = 400
        response_body = {'message': f"El usuario con ID {user_id} no fue encontrado en el grupo de usuarios."}
    except Exception as e:
        status_code = 500
        response_body = {'message': f'Internal Server Error: {e}'}
    finally:
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response

def editUserAtributes(event, user_pool_id):
    # Inicializar el cliente de Cognito
    global status_code, response_body, user_id
    client = boto3.client('cognito-idp')

    try:
        data = json.loads(event['body'])
        user_id = data['user_id']
        name_update = data['name']
        email_update = data['email']
        response_body= client.admin_update_user_attributes(
            UserPoolId=user_pool_id,
            Username=user_id,
            UserAttributes=[
                {
                    'Name': 'name',
                    'Value': name_update
                },
                {
                    'Name': 'email',
                    'Value': email_update
                },
                {
                    'Name': 'email_verified',
                    'Value': "true"
                },
            ]
        )
        status_code = 200

    except KeyError as e:
        response_body = {'message': f'Body mising arguments: {e}'}
    except client.exceptions.UserNotFoundException:
        status_code = 400
        response_body = {'message': f"El usuario con ID {user_id} no fue encontrado en el grupo de usuarios."}
    except Exception as e:
        status_code = 500
        response_body = {'message': f'Internal Server Error: {e}'}
    finally:
        response = {
            'statusCode': status_code,
            'body': json.dumps(response_body),
        }
        return response

def lambda_handler(event, context):
    method = event['httpMethod']

    if method == 'GET':
        USER_POOL_ID = os.environ.get('UserPoolId')
        response = getUsersGroup("estudiantes", USER_POOL_ID)
    elif method == 'PUT':
        USER_POOL_ID = os.environ.get('UserPoolId')
        response = editUserAtributes(event, USER_POOL_ID)
    elif method == 'DELETE':
        USER_POOL_ID = os.environ.get('UserPoolId')
        response = deleteUser(event, USER_POOL_ID)
    else:
        response = {
            'statusCode': 400,
            'body': 'Unsupported HTTP method'
        }

    return response


