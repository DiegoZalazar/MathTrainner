import leccionesController


def lambda_handler(event, context):
    if 'pathParameters' not in event:
        response = {
            'statusCode': 400,
            'body': 'ID not found'
        }

    method = event['httpMethod']

    if method == 'GET':
        response = leccionesController.GetByID(event)
    elif method == 'PUT':
        response = leccionesController.Put(event)
    elif method == 'DELETE':
        response = leccionesController.Delete(event)
    else:
        response = {
            'statusCode': 400,
            'body': 'Unsupported HTTP method'
        }
    return response
