# jaebitly

로컬 실행 시 숏링크 생성 요청

```shell
curl -X POST http://localhost:8080/links \
  -H "Content-Type: application/json" \
  -d '{
    "originalUrl": "https://naver.com"
  }'
```

```shell
curl -I http://localhost:8080/r/{shortId}
```

Baseline Test

```aiignore
hey -disable-redirects -z 30s -c 100 http://localhost:8080/r/a4d2ab
```

```
// hey -disable-redirects -z 30s -c 100 http://localhost:8080/r/a4d2ab

Summary:
  Total:        30.0367 secs
  Slowest:      0.1101 secs
  Fastest:      0.0001 secs
  Average:      0.0030 secs
  Requests/sec: 55549.5928
  

Response time histogram:
  0.000 [1]     |
  0.011 [996404]        |■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  0.022 [973]   |
  0.033 [719]   |
  0.044 [103]   |
  0.055 [311]   |
  0.066 [589]   |
  0.077 [200]   |
  0.088 [398]   |
  0.099 [195]   |
  0.110 [107]   |


Latency distribution:
  10% in 0.0007 secs
  25% in 0.0011 secs
  50% in 0.0015 secs
  75% in 0.0021 secs
  90% in 0.0027 secs
  95% in 0.0032 secs
  99% in 0.0058 secs

Details (average, fastest, slowest):
  DNS+dialup:   0.0000 secs, 0.0001 secs, 0.1101 secs
  DNS-lookup:   0.0000 secs, 0.0000 secs, 0.0221 secs
  req write:    0.0000 secs, 0.0000 secs, 0.0331 secs
  resp wait:    0.0028 secs, 0.0001 secs, 0.1101 secs
  resp read:    0.0001 secs, 0.0000 secs, 0.0205 secs

Status code distribution:
  [302] 1000000 responses
```

Redirect 자체 로직은 p99 < 1ms

이후 성능 저하는 DB / 캐시 / 이벤트 처리 중 하나