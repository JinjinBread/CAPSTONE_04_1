from flask import Flask, render_template, request
import logging
app = Flask(__name__)

# 취업사진 합성 요청
@app.route('/profile/edit',methods=['POST'])
def editPicture():
    # 로그 생성
    logger = logging.getLogger()
    # 로그의 출력 기준 설정
    logger.setLevel(logging.INFO)
    # log 출력 형식
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    # log를 console에 출력
    stream_handler = logging.StreamHandler()
    stream_handler.setFormatter(formatter)
    logger.addHandler(stream_handler)

    photoId = request.form['photoId']
    return render_template('.html')

if __name__=='__main__':
    app.run(host="localhost",port=12300)