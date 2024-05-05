/**
 * @type {import('@types/aws-lambda').APIGatewayProxyHandler}
 */


const mysql = require('mysql');

const connection = mysql.createConnection({
  host     : 'tu-host-de-mysql',
  user     : 'tu-usuario-de-mysql',
  password : 'tu-contraseña-de-mysql',
  database : 'tu-base-de-datos-de-mysql'
});

exports.handler = async (event, context) => {
    const UserParams = {
      UserPoolId: event.userPoolId,
      Username: event.userName,
    };

      connection.connect();
      connection.query('INSERT INTO Estudiante (estudianteSub) VALUES (?)', [UserParams.UserPoolId,UserParams.sub], (error, results, fields) => {
        if (error) throw error;
        console.log('Usuario registrado en MySQL:', results);
      });
      connection.end();
    return event;
  };


