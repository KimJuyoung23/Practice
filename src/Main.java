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
			System.out.print("�� �� : ");
			int menuSel = Integer.parseInt(sc.nextLine());

			if (menuSel == 1) {
				System.out.print("������ IP �ּҸ� �Է����ּ��� : ");
				String ip = sc.nextLine();

				System.out.print("������ ��Ʈ��ȣ�� �Է����ּ��� : ");
				int port = Integer.parseInt(sc.nextLine());

				try {
					client.connect(ip, port);
				} catch (Exception e) {
					System.out.println("���� IP �Ǵ� ��Ʈ��ȣ�� �߸��Ǿ����ϴ�.");
					continue;
				}

				for (int i = 0; i < 3; i++) {
					System.out.println("�α����ϰڽ��ϴ�.");
					System.out.print("���̵� : ");
					String id = sc.nextLine();

					System.out.print("�н����� : ");
					String pw = sc.nextLine();

					try {
						client.login(id, pw);
						isLoginOk = true;
						break;
					} catch (Exception e) {
						System.out.println("���̵� �Ǵ� �н����带 Ȯ�����ּ���.");
					}
				}

				if (isLoginOk) {
					isLoginOk = false;//�α��� �ʱ�ȭ
					System.out.println("�α��ο� �����߽��ϴ�. ");
					while (true) {
						System.out.println("1. File Download");
						System.out.println("2. File Upload");
						System.out.println("3. Connection close");
						System.out.print("�� �� : ");
						int menuSelAfterLogin = Integer.parseInt(sc.nextLine());

						if (menuSelAfterLogin == 1) {
							try {
								String[] fileNames = client.listNames();
								System.out.println("���� �� ���� ����Դϴ�.");
								for (int i = 0; i < fileNames.length; i++) {
									System.out.println(fileNames[i]);
								}
							} catch (Exception e) {
								System.out.println("���� ��� ���ſ� �����߽��ϴ�.");
							}

							System.out.print("�ٿ� ���� ���� �̸� : ");
							String remoteFileName = sc.nextLine();

							System.out.println("(��θ� �Է����ּ���! �����̸��� �ٿ�ε� ���� �̸� �״�� ����˴ϴ�.)");
							System.out.print("�� ��ǻ�Ϳ� ������ ��� : ");

							String localPath = sc.nextLine() + "/" + remoteFileName;
							try {
								client.download(remoteFileName, new File(localPath));
							} catch (Exception e) {
								System.out.println("�ٿ�ε忡 �����߽��ϴ�.");
							}
						} else if (menuSelAfterLogin == 2) {
							System.out.println("���� �̱��� ����Դϴ�.");
						} else if (menuSelAfterLogin == 3) {
							System.out.println("���� ������ �����մϴ�.");
							break;
						} else {
							System.out.println("�޴��� �ٽ� Ȯ�����ּ���.");
							continue;
						}
					}

				} else {// �α��ο� �������� ��
					System.out.println("���̵� �Ǵ� �н����尡 3ȸ�̻� Ʋ�Ƚ��ϴ�. �ٽ� �������ּ���.");
					continue;
				}

			} else if (menuSel == 2) {
				System.out.println("���α׷��� �����մϴ�.");
				System.exit(0);
			} else {
				System.out.println("�޴��� �ٽ� Ȯ�����ּ���.");
			}
		}
	}
}
