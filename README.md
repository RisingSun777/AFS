AFS
===
Designer's notes:
	Make more classes! I combined too many complicated things into just one class 

so the project is a big deal of searching.
Java does not support function pointers =.=" how annoying


Note:
- Ceasar question file is a text file stored in !RES/Quest/ directory.
- Remember to load match's question as soon as the program starts with CTRL + O.

Controls - What a relief! As the control panel kicks in, we don't have to remember tons of 

hotkeys anymore.

Common
ESC - Quit the program
Shift + F1  - Main background
Shift + F2  - BanLinh background
Shift + F3  - Caesar background
Shift + F4  - ThongDiep background
Shift + F5  - ToOng background
Shift + F9	- MainScore background
Shift + F10	- Cover background
F12 - Toggle fullscreen on/off
F1	- Switch current round to DoiDau
F2	- Switch current round to BanLinh
F3	- Switch current round to Caesar
F4	- Switch current round to ThongDiep
F5	- Switch current round to ToOng
F6	- Switch current round to SucManh
Shift + 1	- Add 5 points to team A
Shift + 2	- Add 5 points to team B
Shift + 3	- Add 5 points to team C
Ctrl  + 1	- Subtract 5 points from team A
Ctrl  + 2	- Subtract 5 points from team B
Ctrl  + 3	- Subtract 5 points from team C
Ctrl  + O	- Load a match's question sets in the console
Shift + O	- Edit teams' names in the console
End			- Play main theme
Shift + End	- Stop all sounds


MainRound
Q		- Show question
A		- Show answer
N		- Next question
Ctrl+N	- Previous question

Time manipulator
S					- Start/stop time
PAGE UP				- Add 1 second
PAGE DOWN			- Subtract 1 second
Shift + PgUp		- Reset time to current round
Shift + Ctrl + PgUp - Reset time to 10 seconds in SUCMANHDONGDOI
Shift + PgDn		- Reset time to 0

ThongDiep
Enter		- Hide a cell with a card
1			- Reveal card for team 1. Add letter(s) to its stack
2			- Reveal card for team 2. Add letter(s) to its stack
3			- Reveal card for team 3. Add letter(s) to its stack
Alt + 1		- Remove a char from team 1's stack
Alt + 2		- Remove a char from team 2's stack
Alt + 3		- Remove a char from team 3's stack
N			- Generate new board
R			- Reveal all cards. Only used when the round is over
M			- Activate swap mode in console. To input blank character: 

type "null" (without brackets)

ToOng
1			- Set team 1's piece at current position
2			- Set team 2's piece at current position
3			- Set team 3's piece at current position
4			- Clear piece at current position
C			- Clear board
N			- New board. New game
Arrow keys	- Move current position around the board
O,P			- Cycle turns
Enter		- Select piece
I			- Show/hide index numbers
M			- Skip this move

Quy trinh su dung:
	1. Sau khi chay chuong trinh, se hien ra man hinh Cover. Muon chuyen sang 

background khac phai
		bam Shift + F10 2 lan, dang tim cach fix cho nay.
	2. Shift + F1 de chuyen sang Main round.
	3. Bam Shift + O de dat lai ten cho cac doi o man hinh console cua Eclipse. Luu 

y khong duoc dat
		ten co khoang trang, co dau. Phai thay khoang trang bang "_" hay viet 

lien tuc.
	4. Bam Ctrl + O de load cau hoi 1 tran vao bo nho. Nhap so thu tu cua cac tran. 

Luu y han che nhap
		so khong ton tai, vi du: co 9 tran, nhap 100.
	5. Bam F1 den F6 de doi ten vong hien tai, nham load cau hoi cua vong tuong 

ung. Danh sach co the
		xem trong muc Controls.
	6. Ung voi cac vong, bam Shift + PageUP de reset thoi gian tuong ung. Neu 

khong dung co the dung
		PageUP va PageDOWN de sua lai thoi gian.
	7. Bam Q de hien cau hoi.
	8. Bam S de chay thoi gian, bam them lan nua de dung lai.
	9. Sau khi thoi gian dung, bam A de hien dap an.
	10. Bam A lan nua de tat dap an di. Luu y tai buoc nay neu bam Q no se hien lai 

cau hoi.
	11. Bam N de chuyen sang cau hoi ke. Ctrl + N de tro ve cau hoi phia truoc.
	12. Lap lai buoc 7...
	13. Sau khi da xong 1 tran, tro ve buoc 3 de dat ten cho cac doi moi va load tran 

moi.