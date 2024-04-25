import sesionController


def lambda_handler(event, context):
    if 'pathParameters' not in event:
        response = {
            'statusCode': 400,
            'body': 'ID not found'
        }

    method = event['httpMethod']

    if method == 'GET':
        response = sesionController.GetSesionEjercicio(event)
    elif method == 'POST':
        response = sesionController.PostSesionEjercicio(event)
    else:
        response = {
            'statusCode': 400,
            'body': 'Unsupported HTTP method'
        }
    return response
