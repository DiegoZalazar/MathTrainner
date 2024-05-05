import temasController


def lambda_handler(event, context):
    if 'pathParameters' not in event:
        response = {
            'statusCode': 400,
            'body': 'ID not found'
        }

    method = event['httpMethod']

    if method == 'GET':
        response = temasController.GetByID(event)
    elif method == 'PUT':
        response = temasController.Put(event)
    elif method == 'DELETE':
        response = temasController.Delete(event)
    else:
        response = {
            'statusCode': 400,
            'body':'Unsupported HTTP method'
        }
    return response
