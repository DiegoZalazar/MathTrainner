import examenController


def lambda_handler(event, context):
    method = event['httpMethod']

    if method == 'GET':
        response = examenController.GetExamenInfo(event)
    else:
        response = {
            'statusCode': 400,
            'body': 'Unsupported HTTP method'
        }
    return response
