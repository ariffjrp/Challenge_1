import java.util.*;
import java.io.*;
import java.text.*;

public class Main {
    public static void main(String[] args) {
        int totalHarga = 0;
        Scanner input = new Scanner(System.in);
        ArrayList<String> pesanan = new ArrayList<>();
        ArrayList<Integer> hargaMakanan = new ArrayList<>();
        ArrayList<Integer> qtyMakanan = new ArrayList<>();

        while (true) {
            printMenu();
            int pilihanMenu = input.nextInt();

            if (pilihanMenu == 0) {
                break;
            }

            if (pilihanMenu >= 1 && pilihanMenu <= 5) {
                int qty = getPesanan(input, pilihanMenu);
                int harga = hitungHarga(pilihanMenu, qty);

                pesanan.add(menu(pilihanMenu));
                hargaMakanan.add(harga);
                qtyMakanan.add(qty);

                totalHarga += harga;
            } else if (pilihanMenu == 99) {
                System.out.println("===========================");
                System.out.println("Konfirmasi & Pembayaran");
                System.out.println("===========================");
                cetakPesanan(pesanan, hargaMakanan, qtyMakanan, totalHarga);
                System.out.println("\n1. konfirmasi dan Bayar");
                System.out.println("2. Kembali ke menu utama");
                System.out.println("0. Keluar aplikasi");
                System.out.print("=> ");
                int konfirmasi = input.nextInt();
                if (konfirmasi == 1) {
                    CreatePesanan(pesanan, hargaMakanan, qtyMakanan, totalHarga);
                    System.exit(0);
                } else if (konfirmasi == 2) {
                    continue;
                } else if (konfirmasi == 0) {
                    break;
                }
            } else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }

        input.close();
    }

    private static void printMenu() {
        System.out.println("===========================");
        System.out.println("Selamat datang di BinaryFud");
        System.out.println("===========================");
        System.out.println("1. Nasi Goreng    | 15.000");
        System.out.println("2. Mie Goreng     | 13.000");
        System.out.println("3. Nasi + Ayam    | 18.000");
        System.out.println("4. Es Teh Manis   | 3.000");
        System.out.println("5. Es Jeruk       | 5.000");
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
        System.out.print("\nTentukan pilihan anda : ");
    }

    private static int getPesanan(Scanner input, int pilihanMenu) {
        System.out.println("==========================");
        System.out.println("Berapa pesanan anda");
        System.out.println("==========================");
        System.out.println(pilihanMenu + ". " + menu(pilihanMenu) + "  | " + formatHarga(menuHarga(pilihanMenu)));
        System.out.println("(input 0 untuk kembali)");
        System.out.print("qty => ");
        return input.nextInt();
    }

    private static int menuHarga(int pilihanMenu){
        int[] hargaMakanan = { 15_000, 13_000, 18_000, 3_000, 5_000 };
        return hargaMakanan[pilihanMenu - 1];
    }

    private static int hitungHarga(int pilihanMenu, int qty) {
        int[] hargaMakanan = { 0, 15_000, 13_000, 18_000, 3_000, 5_000 };
        return hargaMakanan[pilihanMenu] * qty;
    }

    private static String menu(int pilihanMenu) {
        String[] menu = { "Nasi Goreng", "Mie Goreng", "Nasi + Ayam", "Es Teh Manis", "Es Jeruk" };
        return menu[pilihanMenu - 1];
    }

    private static void cetakPesanan(ArrayList<String> pesanan, ArrayList<Integer> hargaMakanan, ArrayList<Integer> qtyMakanan, int totalHarga) {
        System.out.println("Pesanan Anda:");
        int totalQty = qtyMakanan.stream().mapToInt(Integer::intValue).sum();

        for (int i = 0; i < pesanan.size(); i++) {
            String namaMakanan = pesanan.get(i);
            int hargaMakananItem = hargaMakanan.get(i);
            int qty = qtyMakanan.get(i);
            System.out.println(namaMakanan + "      " + qty + "     " + formatHarga(hargaMakananItem));
        }
        System.out.println("--------------------------------+");
        System.out.println("Total       " + totalQty + "       " + formatHarga(totalHarga));
    }

    private static void CreatePesanan(ArrayList<String> pesanan, ArrayList<Integer> hargaMakanan, ArrayList<Integer> qtyMakanan, int totalHarga) {
        try {
            BufferedWriter nota = new BufferedWriter(new FileWriter("Nota.txt"));
            nota.write("===========================\n");
            nota.write("BinarFud\n");
            nota.write("===========================\n");
            nota.write("\nTerima kasih sudah memesan\ndi BinarFud\n\n");
            nota.write("Dibawah ini adalah pesanan anda\n\n");
            int totalQty = qtyMakanan.stream().mapToInt(Integer::intValue).sum();
            for (int i = 0; i < pesanan.size(); i++) {
                String namaMakanan = pesanan.get(i);
                int hargaMakananItem = hargaMakanan.get(i);
                int qty = qtyMakanan.get(i);
                nota.write(namaMakanan + "      " + qty + "     " + formatHarga(hargaMakananItem) + "\n");
            }
            nota.write("--------------------------------+\n");
            nota.write("Total            " + totalQty + "     " + formatHarga(totalHarga) + "\n\n");
            nota.write("Pembayaran : BinarCash \n\n");
            nota.write("===========================\n");
            nota.write("Simpan struk ini sebagai\n");
            nota.write("bukti pembayaran\n");
            nota.write("===========================\n");
            nota.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatHarga(int harga) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
        return decimalFormat.format(harga);
    }
}