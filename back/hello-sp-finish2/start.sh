#!/bin/bash

# 1. ngrok 백그라운드로 실행
echo "[1] ngrok 실행 중..."
ngrok start springboot --config ~/.ngrok2/ngrok.yml --log=stdout > 
ngrok.log &
NGROK_PID=$!

# 2. ngrok 주소가 뜰 때까지 잠시 대기
echo "[2] ngrok 주소 수집 중..."
sleep 3

# 3. ngrok 주소 가져오기
NGROK_URL=$(curl -s http://localhost:4040/api/tunnels | grep -o 
'https://[0-9a-zA-Z.-]*\.ngrok-free\.app' | head -n 1)

if [ -z "$NGROK_URL" ]; then
  echo "❌ ngrok 주소를 가져오지 못했습니다."
  kill $NGROK_PID
  exit 1
fi

echo "✅ 현재 ngrok 주소: $NGROK_URL"

# 4. application.properties에 덮어쓰기
echo "[3] application.properties 수정 중..."
sed -i '' "s|^swagger.server-url=.*|swagger.server-url=${NGROK_URL}|" 
./src/main/resources/application.properties

# 5. 스프링 서버 실행
echo "[4] 스프링 서버 실행!"
./gradlew bootRun

# 서버가 종료되면 ngrok도 종료
kill $NGROK_PID

