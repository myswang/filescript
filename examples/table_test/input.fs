person = {}
person['name'] = 'Mike'
person['age'] = 22
person['is_student'] = true
person['fav_foods'] = {}

fav_foods = person['fav_foods']
push(fav_foods, 'Pizza')
push(fav_foods, 'Sushi')
push(fav_foods, 'Ice cream')

print('Name:', person['name'])
print('Age:', person['age'])
print('Are they a student?', person['is_student'])
print('What is their favourite food?', fav_foods[0])
