import random
import requests


TITLE = ["news_id", "user_id", "seconds", "event_type"]
ROWS = 10000


if __name__ == '__main__':

    for i in range(ROWS):
        news_id = random.randint(0, 100)
        user_id = random.randint(0, 1000)
        seconds = random.randint(10, 10000)
        event_type = random.randint(1, 3)
        requests.post('http://localhost:8080/newsinteraction/create',json={'news_id':news_id,'user_id':user_id, 'seconds':seconds, 'event_type':event_type})
