const AWS = require('aws-sdk');

exports.handler = async (event) => {
    // Configura el cliente de AWS Lambda
    const lambda = new AWS.Lambda();
    
    // Parámetros para llamar a la función lambda_destino
    const params = {
        FunctionName: 'CognitoAddDB', // Nombre de la función destino
        InvocationType: 'RequestResponse', // Tipo de invocación, puede ser 'Event' o 'RequestResponse'
        Payload: JSON.stringify({
            "body":{
                "userName" : event.userName
            }
            
        }) // Datos que quieres pasar a la función destino, en este caso, un objeto vacío
    };
    
    try {
        // Llama a la función lambda_destino
        const response = await lambda.invoke(params).promise();
        
        // Procesa la respuesta
        if (response.StatusCode === 200) {
            // La llamada fue exitosa
            console.log("Llamada a lambda_destino exitosa");
        } else {
            // La llamada falló
            console.log("Error al llamar a lambda_destino");
        }
        
        
    } catch (error) {
        console.error("Error al llamar a lambda_destino:", error);
        throw error;
    }finally {

        return;
    };
    
};











