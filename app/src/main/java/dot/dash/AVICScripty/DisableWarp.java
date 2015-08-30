/*DisableWarp sisables AVIC's Warp by deleting BSP and reflashing a proper BSP image
 *Copyright (C) 2015  Inspectifier Wrectifier
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see https://www.gnu.org/licenses/ .
 */

package dot.dash.AVICScripty;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class DisableWarp extends Activity {

    final String BSP="/mnt/udisk/warpdisabledbsp.img";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StringBuilder sb=new StringBuilder();
        File f=new File(BSP);
        if (! new File(BSP).exists()){
            sb.append("BSP Not Found "+ BSP);
            finish();
            return;
        }
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(new String[]{"/system/xbin/su", "-c", "/system/bin/ioctl -l 8 -a 4 /dev/mtd/mtd0 1074285826 2424832 65536 &>/mnt/udisk/ioctllog; dd if="+BSP+" of=/dev/block/mtdblock0 seek=2424832 count=84 bs=1 &>>/mnt/udisk/ioctllog"});
        pb.redirectErrorStream();

        try {
            Process p = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");

                }
                sendToast(sb);
            } catch (IOException e) {
                sb.append(e.toString());
                sendToast(sb);
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            sb.append("device not rooted\n");
            sb.append(e.toString());
            sendToast(sb);
        }
        finish();
    }

    private void sendToast(StringBuilder sb) {
        Context context = getApplicationContext();


        int duration = 100000;

        Toast toast = Toast.makeText(context, sb.toString(), duration);
        toast.show();
    }


}
