import ejerciciosController


def lambda_handler(event, context):
    if 'pathParameters' not in event:
        response = {
            'statusCode': 400,
            'body': 'ID not found'
        }
        
    method = event['httpMethod']

    # Get all modulos
    if method == 'GET':
        response = ejerciciosController.GetAll(event)
    elif method == 'POST':
        response = ejerciciosController.Post(event)
    else:
        response = {
            'statusCode': 400,
            'body':'Unsupported HTTP method'
        }
    return response
