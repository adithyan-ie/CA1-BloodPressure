import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  vus: 10,
  duration: '30s',
  thresholds: {
    http_req_duration: ['p(95)<500'],
    http_req_failed: ['rate<0.01'],
  }
};

export default function () {
  const url = 'http://localhost:8080/bp';

  const payload = JSON.stringify({
    systolic: 170,
    diastolic: 85,
    category: ''
  });

  const headers = { 'Content-Type': 'application/json' };

  const res = http.post(url, payload, { headers });

  check(res, {
    'status is 201/200': r => r.status === 201 || r.status === 200
  });

  sleep(1);
}
