export const LOGIN = {
  EMAIL: 'fquesada@itba.edua.ar',
  TOKEN:
    'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcXVlc2FkYUBpdGJhLmVkdWEuYXIiLCJpc3MiOiJoaXJlbmV0LmNvbSIsImlhdCI6MTYzODU2MDcwNywiZXhwIjoxNjM5MTY1NTA3fQ.Jwn8pIQDylfKPwHONwwdTptA2mLvJ9SJAjgBjqEJRzkZ5BkXWyU7b6WMPrcayk6IlFpwPeEnzCqJd7bdcDgCgQ',
  get USER() {
    return {
      'contracts': 'http://localhost:8080/paw-2021a-03/api/contracts?userId=1',
      'email': this.EMAIL,
      'id': 1,
      'image': 'http://localhost:8080/paw-2021a-03/api/users/1/image',
      'phone': '12345678',
      'roles': ['CLIENT'],
      'username': 'Francisco Quesada',
    };
  },
};
