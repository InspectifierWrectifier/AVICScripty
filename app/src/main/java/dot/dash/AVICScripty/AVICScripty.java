/*AVICScripty executes /mnt/udisk/scripty.sh with root permissions.  You must have root.
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


public class AVICScripty extends Activity {

    final String SCRIPTYSCRIPT="/mnt/udisk/scripty.sh";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StringBuilder sb=new StringBuilder();

        File f=new File(SCRIPTYSCRIPT);
        if (! new File(SCRIPTYSCRIPT).exists()){
            sb.append("Scripty! script not found at "+ SCRIPTYSCRIPT);
        }
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(new String[]{"/system/xbin/su", "-c", "/system/bin/sh "+SCRIPTYSCRIPT});
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
