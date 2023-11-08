import translate_test as t
from konlpy.tag import Okt

def score(input_answer, my_answer):
    for i in range(len(input_answer)):
        if(input_answer[i].type == 'eng_to_kor'):
            if(eng_to_kor(input_answer[i],my_answer[i])):
                my_answer[i].ox = 'o'
            else:
                my_answer[i].ox = 'x'
        elif(input_answer[i].type == 'kor_to_eng'):
            if(input_answer[i].answer == my_answer[i]):
                my_answer[i].ox = 'o'
            else:
                my_answer[i].ox = 'x'

def eng_to_kor(answer, my_answer):
    my_kor_ans = t.translate(my_answer)
    for i in answer:
        if(answer == my_kor_ans):
            return 1
    return 0