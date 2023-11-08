import random
import translate_test as t

# 텍스트 파일에서 단어를 읽어옵니다.
def make_test(src):
    with open(src, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    # 랜덤하게 20개의 단어를 선택합니다.
    random_lines = random.sample(lines, 20)

# 선택된 단어와 뜻을 파싱하여 출력합니다.
    for line in random_lines:
        # 줄 바꿈 문자를 제거하고, 콜론(:)을 기준으로 단어와 뜻을 분리합니다.
        word, meaning, correct, cnt  = line.strip().split(' : ')
        print(f'단어: {word}, 뜻: {meaning}, 번역된 뜻: {t.translate(meaning)}')
        


make_test("middle3.txt")