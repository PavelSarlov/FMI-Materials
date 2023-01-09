export const protocol = 'http';
export const backendHost = 'localhost';
export const backendPort = '8080';
export const backendApi = `${protocol}://${backendHost}:${backendPort}/api`;

export const environment = {
  production: true,
  usersApi: `${backendApi}/users`,
  coursesApi: `${backendApi}/courses`,
  departmentsApi: `${backendApi}/departments`,
  authApi: `${backendApi}/auth`,
  adminsApi: `${backendApi}/admins`,
};
