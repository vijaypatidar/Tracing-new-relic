import http from 'k6/http';
import { sleep } from 'k6';

export default function () {
  const app1 = 'http://localhost:8081/data1';
  const payload = JSON.stringify({ "username": "vija" });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const res = http.post(app1, payload, params);
  const body = JSON.parse(res.body);
  console.log(body);

  sleep(2)
  const app2 = 'http://localhost:8080/data1';
  const payload1 = JSON.stringify({ "username": "vija", traceId: body.traceId });



  const res1 = http.post(app2, payload1, params);
  const body1 = JSON.parse(res1.body);
  console.log(body1);
}
