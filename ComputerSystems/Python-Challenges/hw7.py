# Computer Systems HW7
# Programs written by Anish Dhesikan

#Python > List-1 > sum3
def sum3(nums):
    return sum(nums)

#Python > List-1 > rotate_left3
def rotate_left3(nums):
    return [nums[(i + 1) % (len(nums))] for i in range (len(nums))]

#Python > List-1 > max_end3
def max_end3(nums):
    return [max(nums[0], nums[2]), max(nums[0], nums[2]), max(nums[0], nums[2])]

#Python > List-1 > make_ends
def make_ends(nums):
    return [nums[0], nums[(len(nums)) - 1]]

#Python > List-1 > has23
def has23(nums):
    return 2 in nums or 3 in nums

#Python > List-2 > count_evens
def count_evens(nums):
    return len( [x for x in nums if x % 2 == 0] )

#Python > List-2 > sum13
def sum13(nums):
    return  sum( [nums[i] for i in range (len(nums)) if nums[i] != 13 and (i==0 or nums[i - 1] != 13)] )

#Python > List-2 > big_diff
def big_diff(nums):
    return max(nums) - min(nums)

#Python > List-2 > sum67
def sum67(nums):
   while 6 in nums:
     rest = nums[nums.index(6):]
     nums = nums[:nums.index(6)] + rest[rest.index(7)+1:]
   return sum(nums)

#Python > List-2 > centered_average
def centered_average(nums):
    return (sum(nums) - max(nums) - min(nums)) / (len(nums) - 2)

#Python > List-2 > has22
def has22(nums):
    return any(nums[i] == nums[i+1] == 2 for i in range(len(nums)-1))

#Python > String-1 > extra_end
def extra_end(str):
    return 3*str[-2:]

#Python > String-1 > without_end
def without_end(str):
    return str[1:-1]

#Python > String-2 > double_char
def double_char(str):
    return "".join([ c+c for c in str ])

#Python > String-2 > count_code
def count_code(str):
    return len([i for i in range(len(str)-3) if str[i:i+2] == 'co' and str[i+3] == 'e'])

#Python > String-2 > count_hi
def count_hi(str):
    return str.count('hi')

#Python > String-2 > end_other
def end_other(a, b):
    return a[-len(b):].lower() == b.lower() or b[-len(a):].lower() == a.lower()

#Python > String-2 > cat_dog
def cat_dog(str):
    return str.count("cat") == str.count("dog")

#Python > String-2 > xyz_there
def xyz_there(str):
    return str.count("xyz") - str.count(".xyz") > 0



# Part 2:
#     Re-read hw2.  There are two programs that you were asked to write in C:
#     Program 1 reports:  [lines, words, chars]
#     Program 2 reports the frequency count for:
#         ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', whitespace, other chars ]
#             Write Program 1 and Program 2 in Python.  In each case, you just need to
# write one function, whose argument is a string.  It should return
#     the required list.  For me, the function for Program 1 required one
#     line in the body of the function.  For me, the function for Program
#     2 required two line in the body of the function.



def wordCount(str):
    return [(len(str.split("\n"))), (len(str.split(" "))), (len(str))]

def mycount(str):
    mycount = [ str.count(c) for c in "0123456789 \t\n" ]
    return [mycount[i] if i < ((len(mycount)) - 3) else mycount[i] + mycount[i+1] + mycount[i+2] if i < ((len(mycount)) - 2) else (len(str)) - sum(mycount) for i in range ((len(mycount)) - 1)]











