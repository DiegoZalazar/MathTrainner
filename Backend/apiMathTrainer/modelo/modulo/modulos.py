import modulosController


def lambda_handler(event, context):
    method = event['httpMethod']

    # Get all modulos
    if method == 'GET':
        response = modulosController.GetAll()
    elif method == 'POST':
        response = modulosController.Post(event)
    else:
        response = {
            'statusCode': 400,
            'body': 'Unsupported HTTP method'
        }
    return response
