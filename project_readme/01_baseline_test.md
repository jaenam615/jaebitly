## Redirect Performance Baseline (J-013)

Redirect API의 성능 특성을 파악하기 위해,
DB·캐시·외부 IO가 없는 상태에서 **Baseline 성능 측정**을 수행했다.

본 측정의 목적은 최적화나 목표 수치 달성이 아니라,
현재 코드 구조 자체가 어느 수준까지 처리 가능한지를 파악하고
이후 설계 변경(DB 도입, 캐시 적용 등)의 **비교 기준점**을 확보하는 것이다.

---

### Test Setup

- Endpoint: `GET /r/{shortKey}`
- Environment:
    - Local
    - Single instance
    - In-memory data
- Tool: `hey`
- Redirect follow: **disabled**
    - Redirect 이후 외부 응답을 배제하고, 서버의 응답 성능만 측정
- Command:

```bash
hey -disable-redirects -z 30s -c 100 http://localhost:8080/r/{shortKey}
```

```bash
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

---
Result (Concurrency = 100)

Throughput

Requests/sec: ~55,549 RPS

Latency

p50: ~1.5 ms

p95: ~3.2 ms

p99: ~5.8 ms

Slowest: ~110 ms

Status Code

302 responses only (redirect follow disabled)

---

Interpretation

Redirect 요청 처리 로직 자체는 매우 낮은 latency를 보이며,
p99 기준으로도 단일 자릿수 ms 수준에서 안정적으로 동작한다.

tail latency(최대 ~110ms)는 GC, OS scheduling 등 비결정적 요인으로 보이며,
구조적인 병목 징후는 관찰되지 않았다.

본 결과는 DB 및 외부 의존성이 추가되기 전의 성능 상한선으로,
이후 성능 변화의 원인을 분리해서 분석하기 위한 기준점으로 사용한다.

---

Notes

본 측정은 실제 서비스 성능을 의미하지 않는다.

DB, 캐시, 이벤트 처리 방식 변경 시 동일한 방식으로 재측정하여
성능 변화의 원인을 단계별로 비교할 예정이다.

---