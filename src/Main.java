import java.io.File;
import java.util.Scanner;

import it.sauronsoftware.ftp4j.FTPClient;

public class Main {
	public static void main(String[] args) {
		FTPClient client = new FTPClient();
		Scanner sc = new Scanner(System.in);

		boolean isLoginOk = false;

		while (true) {
			System.out.println("== FTP Client Utility == ");
			System.out.println("1. FTP Connect");
			System.out.println("2. Exit Program");
			System.out.print("선 택 : ");
			int menuSel = Integer.parseInt(sc.nextLine());

			if (menuSel == 1) {
				System.out.print("서버의 IP 주소를 입력해주세요 : ");
				String ip = sc.nextLine();

				System.out.print("서버의 포트번호를 입력해주세요 : ");
				int port = Integer.parseInt(sc.nextLine());

				try {
					client.connect(ip, port);
				} catch (Exception e) {
					System.out.println("서버 IP 또는 포트번호가 잘못되었습니다.");
					continue;
				}

				for (int i = 0; i < 3; i++) {
					System.out.println("로그인하겠습니다.");
					System.out.print("아이디 : ");
					String id = sc.nextLine();

					System.out.print("패스워드 : ");
					String pw = sc.nextLine();

					try {
						client.login(id, pw);
						isLoginOk = true;
						break;
					} catch (Exception e) {
						System.out.println("아이디 또는 패스워드를 확인해주세요.");
					}
				}

				if (isLoginOk) {
					isLoginOk = false;//로그인 초기화
					System.out.println("로그인에 성공했습니다. ");
					while (true) {
						System.out.println("1. File Download");
						System.out.println("2. File Upload");
						System.out.println("3. Connection close");
						System.out.print("선 택 : ");
						int menuSelAfterLogin = Integer.parseInt(sc.nextLine());

						if (menuSelAfterLogin == 1) {
							try {
								String[] fileNames = client.listNames();
								System.out.println("서버 측 파일 목록입니다.");
								for (int i = 0; i < fileNames.length; i++) {
									System.out.println(fileNames[i]);
								}
							} catch (Exception e) {
								System.out.println("파일 목록 수신에 실패했습니다.");
							}

							System.out.print("다운 받을 파일 이름 : ");
							String remoteFileName = sc.nextLine();

							System.out.println("(경로만 입력해주세요! 파일이름은 다운로드 파일 이름 그대로 저장됩니다.)");
							System.out.print("내 컴퓨터에 저장할 경로 : ");

							String localPath = sc.nextLine() + "/" + remoteFileName;
							try {
								client.download(remoteFileName, new File(localPath));
							} catch (Exception e) {
								System.out.println("다운로드에 실패했습니다.");
							}
						} else if (menuSelAfterLogin == 2) {
							System.out.println("현재 미구현 기능입니다.");
						} else if (menuSelAfterLogin == 3) {
							System.out.println("서버 연결을 종료합니다.");
							break;
						} else {
							System.out.println("메뉴를 다시 확인해주세요.");
							continue;
						}
					}

				} else {// 로그인에 실패했을 때
					System.out.println("아이디 또는 패스워드가 3회이상 틀렸습니다. 다시 접속해주세요.");
					continue;
				}

			} else if (menuSel == 2) {
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			} else {
				System.out.println("메뉴를 다시 확인해주세요.");
			}
		}
	}
}
