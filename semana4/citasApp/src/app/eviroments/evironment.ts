export  const localhost = 'http://localhost';

export const environment = {
    apiUrl:localhost.concat(':8090/api/'),
    authUrl:localhost.concat(':9000/api/login'),
    apiUsuario:localhost.concat(':9000/admin/usuarios')
}