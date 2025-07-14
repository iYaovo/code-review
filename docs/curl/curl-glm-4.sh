curl -X POST \
        -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsInNpZ25fdHlwZSI6IlNJR04ifQ.eyJhcGlfa2V5IjoiNzU1NzVmN2FjMzM5NDA0NDljODZiNTg4MjcyZDk0MDgiLCJleHAiOjE3NTI1MDM3MDA4OTksInRpbWVzdGFtcCI6MTc1MjUwMTkwMDkwNn0.sBo3762RzuXPG6li7kLmG6sgeexrMalIMqJkcDNw4JQ" \
        -H "Content-Type: application/json" \
        -H "User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" \
        -d '{
          "model":"glm-4",
          "stream": "true",
          "messages": [
              {
                  "role": "user",
                  "content": "1+1"
              }
          ]
        }' \
  https://open.bigmodel.cn/api/paas/v4/chat/completions