import ejerciciosController


def lambda_handler(event, context):
    if 'pathParameters' not in event:
        response = {
            'statusCode': 400,
            'body': 'ID not found'
        }

    method = event['httpMethod']

    if method == 'GET':
        response = ejerciciosController.GetByID(event)
    elif method == 'PUT':
        response = ejerciciosController.Put(event)
    elif method == 'DELETE':
        response = ejerciciosController.Delete(event)
    else:
        response = {
            'statusCode': 400,
            'body': 'Unsupported HTTP method'
        }
    return response
