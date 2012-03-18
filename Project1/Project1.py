"""
A version of Project1.java as a python script.
"""

from random import Random

# since this is python, after all, it'll have a much more "scripty" feel; i.e: prompts.
print 'PyShamBo?'
user_name = raw_input('What\'s your name?\n')
user_hand = raw_input('Now let\'s see if you can beat me.\nWhat\'s your hand?\n')

# same as the r = Math.random() and pcHand assignment block (main:27-37)
r = Random()
x = r.randint(1, 3)

if x==1:
    pc_hand = 'rock'
elif x==2:
    pc_hand = 'scissors'
elif x==3:
    pc_hand = 'paper'

# compare hands
if user_hand == pc_hand:
    winner = 'Tie!'
elif user_hand == 'rock':
    if pc_hand == 'scissors':
        winner = user_name + ' wins!'
    else:
        winner = 'The computer wins!'
elif user_hand == 'scissors':
    if pc_hand == 'rock':
        winner = 'The computer wins!'
    else:
        winner = user_name + ' wins!'
elif user_hand == 'paper':
    if pc_hand == 'rock':
        winner = user_name + ' wins!'
    else:
        winner = 'The computer wins!'
else:
    winner = 'dammit...'

# ...and, output.
print '************************************************************'
print 'User name: %s' % user_name
print 'User hand: %s' % user_hand
print 'Computer hand: %s' % pc_hand
print winner
print '************************************************************'