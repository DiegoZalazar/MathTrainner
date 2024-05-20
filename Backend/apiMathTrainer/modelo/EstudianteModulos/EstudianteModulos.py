import EstudianteModulosController


def lambda_handler(event, context):
    method = event['httpMethod']

    if method == 'GET':
        response = EstudianteModulosController.GetModulosAvance(event)
    elif method == 'PUT':
        response = EstudianteModulosController.ResetModulosAvance(event)
    else:
        response = {
            'statusCode': 400,
            'body': 'Unsupported HTTP method'
        }
    return response
