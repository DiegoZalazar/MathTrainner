import examenController


def lambda_handler(event, context):
    if 'pathParameters' not in event:
        response = {
            'statusCode': 400,
            'body': 'ID not found'
        }

    method = event['httpMethod']

    if method == 'GET':
        response = examenController.Get(event)
    elif method == 'POST':
        response = examenController.Post(event)
    else:
        response = {
            'statusCode': 400,
            'body': 'Unsupported HTTP method'
        }
    return response
