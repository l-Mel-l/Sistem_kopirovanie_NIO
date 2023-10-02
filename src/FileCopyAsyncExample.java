import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class FileCopyAsyncExample {

    public static void main(String[] args) {
        Path sourceFile1 = Path.of("C:\\Users\\79623\\OneDrive\\Рабочий стол\\Ассинхронное поироваине\\Откуда копировать 1.txt");
        Path sourceFile2 = Path.of("C:\\Users\\79623\\OneDrive\\Рабочий стол\\Ассинхронное поироваине\\Откуда копировать 2.txt");
        Path targetFile1 = Path.of("C:\\Users\\79623\\OneDrive\\Рабочий стол\\Ассинхронное поироваине\\Куда копировать 1.txt");
        Path targetFile2 = Path.of("C:\\Users\\79623\\OneDrive\\Рабочий стол\\Ассинхронное поироваине\\Куда копировать 2.txt");

        long startTime = System.currentTimeMillis();

        try {
            AsynchronousFileChannel sourceChannel1 = AsynchronousFileChannel.open(sourceFile1, StandardOpenOption.READ);
            AsynchronousFileChannel sourceChannel2 = AsynchronousFileChannel.open(sourceFile2, StandardOpenOption.READ);
            AsynchronousFileChannel targetChannel1 = AsynchronousFileChannel.open(targetFile1, StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE);
            AsynchronousFileChannel targetChannel2 = AsynchronousFileChannel.open(targetFile2, StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE);

            ByteBuffer buffer1 = ByteBuffer.allocate(1024);
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);

            Future<Integer> future1 = sourceChannel1.read(buffer1, 0);
            Future<Integer> future2 = sourceChannel2.read(buffer2, 0);

            while (future1.isDone() == false || future2.isDone() == false) {
                // ждем завершения чтения
            }

            buffer1.flip();
            buffer2.flip();

            targetChannel1.write(buffer1, 0);
            targetChannel2.write(buffer2, 0);

            buffer1.clear();
            buffer2.clear();

            sourceChannel1.close();
            sourceChannel2.close();
            targetChannel1.close();
            targetChannel2.close();

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Оба файла успешно скопированы асинхронно. Затраченное время: " + duration + " мс");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}