import leccionesController


def lambda_handler(event, context):
    if 'pathParameters' not in event:
        response = {
            'statusCode': 400,
            'body': 'ID not found'
        }
        
    method = event['httpMethod']

    # Get all modulos
    if method == 'GET':
        response = leccionesController.GetAll(event)
    elif method == 'POST':
        response = leccionesController.Post(event)
    else:
        response = {
            'statusCode': 400,
            'body':'Unsupported HTTP method'
        }
    return response
