import temasController


def lambda_handler(event, context):
    method = event['httpMethod']

    # Get all modulos
    if method == 'GET':
        response = temasController.GetAll()
    elif method == 'POST':
        response = temasController.Post(event)
    else:
        response = {
            'statusCode': 400,
            'body': 'Unsupported HTTP method'
        }
    return response
