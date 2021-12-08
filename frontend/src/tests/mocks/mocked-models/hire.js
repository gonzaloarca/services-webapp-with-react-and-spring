export const HIRE = {
  JOB_POST_ID: 25,
  JOB_PACKAGE_ID: 31,
  get JOB_POST() {
    return {
      'active': true,
      'availableHours': 'lunes a viernes de 8am a 8pm',
      'creationDate': '2021-11-28T22:21:37.404',
      'id': this.JOB_POST_ID,
      'images': [
        'http://pawserver.it.itba.edu.ar/paw-2021a-03/api/job-posts/25/images/27',
      ],
      'jobType': {
        'description': 'Carpentry',
        'id': 2,
      },
      'packages':
        'http://pawserver.it.itba.edu.ar/paw-2021a-03/api/job-posts/25/packages',
      'professional':
        'http://pawserver.it.itba.edu.ar/paw-2021a-03/api/users/38',
      'reviews':
        'http://pawserver.it.itba.edu.ar/paw-2021a-03/api/reviews?postId=25',
      'title': 'muebles de madera patagonica',
      'zones': [
        {
          'description': 'Agronomia',
          'id': 0,
        },
        {
          'description': 'Boedo',
          'id': 5,
        },
        {
          'description': 'Flores',
          'id': 11,
        },
        {
          'description': 'Mataderos',
          'id': 16,
        },
        {
          'description': 'Nueva Pompeya',
          'id': 19,
        },
        {
          'description': 'Nuñez',
          'id': 20,
        },
        {
          'description': 'Parque Patricios',
          'id': 25,
        },
        {
          'description': 'Saavedra',
          'id': 29,
        },
        {
          'description': 'San Nicolas',
          'id': 31,
        },
        {
          'description': 'Villa del Parque',
          'id': 36,
        },
        {
          'description': 'Villa Devoto',
          'id': 37,
        },
        {
          'description': 'Villa Luro',
          'id': 40,
        },
        {
          'description': 'Villa Ortuzar',
          'id': 41,
        },
        {
          'description': 'Villa Real',
          'id': 43,
        },
        {
          'description': 'Villa Urquiza',
          'id': 47,
        },
        {
          'description': 'Villa Riachuelo',
          'id': 44,
        },
        {
          'description': 'Villa Santa Rita',
          'id': 45,
        },
        {
          'description': 'Villa Soldati',
          'id': 46,
        },
      ],
    };
  },
  get JOB_PACKAGE() {
    return {
      'active': true,
      'description': 'presupuesto gratis',
      'id': this.JOB_PACKAGE_ID,
      'jobPost':
        'http://pawserver.it.itba.edu.ar/paw-2021a-03/api/job-posts/25',
      'rateType': {
        'description': 'TBD',
        'id': 2,
      },
      'title': 'Desarrollo de muebles a pedido',
    };
  },
  PRO_USER: {
    'contracts':
      'http://pawserver.it.itba.edu.ar/paw-2021a-03/api/contracts?userId=38',
    'email': 'rodriguezmanueljoaquin+01010@gmail.com',
    'id': 38,
    'phone': '+541164485598',
    'roles': ['CLIENT', 'PROFESSIONAL'],
    'username': 'asdasdasd',
  },
};
