import sys
import logging
import pymysql
import json
import os


# Configure mySQL RDS DB connection
conn = pymysql.connect(
        host=os.environ['HOST'],
        user=os.environ['USER'],
        passwd=os.environ['PASSWORD'],
        db=os.environ['DB'],
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
)


#aws lambda function
def lambda_handler(event, context):
    if 'pathParameters' not in event:
        return {
            'statusCode': 400,
            'body': json.dumps('ID not found')
        }

    idModulo = event['pathParameters']['idModulo']

    query = f'Select * from Modulo WHERE idModulo = ${idModulo}'
    # Execute query
    try:

        cursor = conn.cursor()

        cursor.execute(query)
        result = cursor.fetchall()

        return {
            'statusCode': 200,
            'body': json.dumps(result)
        }

    except Exception as e:
        return {
            'statusCode': 500,
            'body': json.dumps(f"Error: {str(e)}")
        }

    finally:
        # Close connection
        #todo check that the connection is closed
        if conn:
            conn.close()




