import com.sun.security.jgss.GSSUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class Welcome {
    static final int NUM_BOOK = 3;
    static final int NUM_ITEM = 7;
    static Scanner sc = new Scanner(System.in);
    static CartItem[] mCartItem = new CartItem[NUM_BOOK];
    static int mCartCount = 0;
    public static void main(String[] args) {
        String[][] mBook = new String[NUM_BOOK][NUM_ITEM];

        System.out.print("당신의 이름을 입력하세요 : ");
        String userName = sc.next();
        System.out.print("연락처를 입력하세요 : ");
        String userMobile = sc.next();

        //String 클래스의 repeat() 메소드를 활용해서 * 원하는 만큼 찍기
        String greeting = "\tWelcome to Shopping Mall";
        String tagLine = "\tWelcome to Book Market!";
        boolean quit = false;
        while (!quit) {
            System.out.println("*".repeat(50));
            System.out.println("\t\t" + greeting);
            System.out.println("\t\t" + tagLine);

            menuIntroduction();

            System.out.print("메뉴 번호를 선택해주세요 ");
            int n = sc.nextInt();
            if (n<1 || n>8) {
                System.out.println("1부터 8까지의 숫자를 입력하세요.");
            } else {
                switch (n) {
                    case 1: //고객 정보 확인
                        menuGuestInfo(userName, userMobile);
                        break;
                    case 2: //장바구니 상품 목록 보기
                        menuCartItemList();
                        break;
                    case 3: //장바구니 비우기
                        menuCartClear();
                        break;
                    case 4: //장바구니에 항목 추가하기
                        menuCartAddItem(mBook);
                        break;
                    case 5: //장바구니의 항목 수량 줄이기
                        menuCartRemoveItemCount();
                        break;
                    case 6: //장바구니의 항목 삭제하기
                        menuCartRemoveItem();
                        break;
                    case 7: //영수증 표시하기
                        menuCartBill();
                        break;
                    case 8: //종료
                        menuExit();
                        quit = true;
                        break;
                }
            }
        }
    }
    public static void menuIntroduction() {
        System.out.println("*".repeat(50));
        System.out.println("1. 고객 정보 확인하기 \t\t4. 바구니에 항목 추가하기");
        System.out.println("2. 장바구니 상품 목록 보기 \t5. 장바구니의 항목 수량 줄이기");
        System.out.println("3. 장바구니 비우기 \t\t6. 장바구니의 항목 삭제하기");
        System.out.println("7. 영수증 표시하기 \t\t8. 종료");
        System.out.println("*".repeat(50));
    }
    public static void menuGuestInfo(String name, String mobile) {
        System.out.println("현재 고객 정보 : ");
        System.out.println("이름 " + name + " 연락처 " + mobile);
    }
    public static void menuCartItemList() {
        System.out.println("2. 장바구니 상품 목록 보기");
        System.out.println("-".repeat(50));
        System.out.println("     도서ID \t|     수량\t|     합계");
        for (int i=0; i<mCartCount; i++) {
            System.out.print("     " + mCartItem[i].getBookID() + " \t| ");
            System.out.print("     " + mCartItem[i].getQuantity() + " \t| ");
            System.out.print("     " + mCartItem[i].getTotalPrice());
            System.out.println();
        }
        System.out.println("-".repeat(50));
    }
    public static void menuCartClear() {
        System.out.println("3. 장바구니 비우기");
    }
    public static void menuCartAddItem(String[][] book) {
        System.out.println("4. 장바구니에 항목 추가하기");
        BookList(book);
        for (int i=0; i<NUM_BOOK; i++) {
            for (int j=0; j<NUM_ITEM; j++) {
                System.out.print(book[i][j] + " | ");
            }
            System.out.println();
        }
        boolean quit = false;

        while (!quit) {
            System.out.println("장바구니에 추가할 도서의 ID를 입력하세요 : ");
            String str = sc.nextLine();

            boolean flag = false;
            int numId = -1;

            for (int i=0; i<NUM_BOOK; i++) {
                if (str.equals(book[i][0])) {
                    numId = i;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                System.out.println("장바구니에 추가하겠습니까? Y | N");
                str = sc.nextLine();
                if (str.toUpperCase().equals("Y")) {
                    System.out.println(book[numId][0] + " 도서가 장바구니에 추가되었습니다.");
                    if (!isCartInBook(book[numId][0])) {
                        mCartItem[mCartCount++] = new CartItem(book[numId]);
                    }
                }
                quit = true;
            } else {
                System.out.println("다시 입력해 주세요.");
            }
        }
    }
    public static void menuCartRemoveItemCount() {
        System.out.println("5. 장바구니의 항목 수량 줄이기");
    }
    public static void menuCartRemoveItem() {
        System.out.println("6. 장바구니의 항목 삭제하기");
    }
    public static void menuCartBill() {
        System.out.println("7. 영수증 표시하기");
    }
    public static void menuExit() {
        System.out.println("8. 종료");
    }
    public static void BookList(String[][] book) {
        Scanner sc = new Scanner(System.in);
        for (int i=0; i<NUM_BOOK; i++) {
            System.out.printf("%d 번째 정보를 입력하세요 : %n", i+1);
            for (int j=0; j<NUM_ITEM; j++) {
                System.out.print("해당 책의 정보를 7번으로 나누어 입력하세요.");
                book[i][j] = sc.nextLine(); //
            }
        }
//        book[0][0] = "ISBN1234";
//        book[0][1] = "쉽게 배우는 JSP 웹 프로그래밍";
//        book[0][2] = "27000";
//        book[0][3] = "송미영";
//        book[0][4] = "단계별로 쇼핑몰을 구현하며 배우는 JSP 웹 프로그래밍";
//        book[0][5] = "IT전문서";
//        book[0][6] = "2018/10/08";
//
//        book[1][0] = "ISBN1235";
//        book[1][1] = "안드로이드 프로그래밍";
//        book[1][2] = "33000";
//        book[1][3] = "우재남";
//        book[1][4] = "실습 단계별 명쾌한 멘토링!";
//        book[1][5] = "IT전문서";
//        book[1][6] = "2022/06/10";
//
//        book[2][0] = "ISBN1236";
//        book[2][1] = "스크래치";
//        book[2][2] = "22000";
//        book[2][3] = "고광일";
//        book[2][4] = "컴퓨팅 사고력을 키우는 블록 코딩";
//        book[2][5] = "컴퓨터입문";
//        book[2][6] = "2019/06/10";
        public static boolean isCartInBook(String bookId) {
            boolean flag = false;
            for (int i=0; i<mCartCount; i++) {
                if (bookId == mCartItem[i].getBookID()) {
                    mCartItem[i].setQuantity(mCartItem[i].getQuantity()+1);
                    flag = true;
                }
            }
            return flag;
        }
    }
}
