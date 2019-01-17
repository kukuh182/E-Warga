package id.bengkelaplikasi.ewarga.helper;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class Constanta {

    public static final String TYPE_REQUEST = "mobile_ewarga";

    public static class Preference {
        public static final String ID       = "EWARGA_ID";
        public static final String USERNAME = "EWARGA_USERNAME";
        public static final String PASSWORD = "EWARGA_PASSWORD";
        public static final String DEVICEID = "EWARGA_DEVICEID";
        public static final String PRIVINFO = "EWARGA_PRIVINFO";
        public static final String RMEMBERID = "EWARGA_RMEMBERID";
        public static final String GROUP_DARURAT = "GROUP_DARURAT";
        public static final LatLng CENTER_INA = new LatLng(-6.7535739, 106.63140096);
    }

    public static class RegPreference {
        public static final String PROV_KODE = "PROV_KODE";
        public static final String PROV_NAMA = "PROV_NAMA";
        public static final String KOTA_KODE = "KOTA_KODE";
        public static final String KOTA_NAMA = "KOTA_NAMA";
        public static final String KEC_KODE = "KEC_KODE";
        public static final String KEC_NAMA = "KEC_NAMA";
        public static final String KEC_KDPOS = "KEC_KDPOS";
        public static final String KEL_KODE = "KEL_KODE";
        public static final String KEL_NAMA = "KEL_NAMA";
        public static final String STAT_WILAYAH = "STAT_WILAYAH";
    }


    public static class Url {
        public static final String APP = "http://bengkelaplikasi.net/api_ewarga/";
    }

    public static class Message {
        public static final String ERR_CONNECTION = "Koneksi Tidak Stabil";
        public static final String ERR_SERVER_DOWN = "Server Down";
        public static final String ERR_RESPONSE = "Response Error";
        public static final String ERR_FAIED_DATA = "Failed Data";

        public static final String FAILURE = "Connection Failure";
        public static final String ERROR_RESPONSE = "Error Response";
        public static final String UNSUCCESS_RESPONSE = "Unsuccess Response";
        public static final String ERROR_PARSING = "Error Parsing";
    }

    public static class Config {
        public static final String DATABASE_NAME = "ewarga.realm";
    }


    public static class Libraries {
        public static final String [] title = {"Retrofit", "Glide", "Butter Knife",
                "Blogcat Expandable Textview", "Afollestad Material Dialog", "Realm", "Flaticon"};

        public static class Content{

            public static final String retrofit = "Copyright 2013 Square, Inc.\n" +
                    "\n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    "you may not use this file except in compliance with the License.\n" +
                    "You may obtain a copy of the License at\n" +
                    "\n" +
                    "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                    "\n" +
                    "Unless required by applicable law or agreed to in writing, software\n" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "See the License for the specific language governing permissions and\n" +
                    "limitations under the License.";

            public static final String glide = "License for everything not in third_party and not otherwise marked:\n" +
                    "\n" +
                    "Copyright 2014 Google, Inc. All rights reserved.\n" +
                    "\n" +
                    "Redistribution and use in source and binary forms, with or without modification, are\n" +
                    "permitted provided that the following conditions are met:\n" +
                    "\n" +
                    "   1. Redistributions of source code must retain the above copyright notice, this list of\n" +
                    "         conditions and the following disclaimer.\n" +
                    "\n" +
                    "   2. Redistributions in binary form must reproduce the above copyright notice, this list\n" +
                    "         of conditions and the following disclaimer in the documentation and/or other materials\n" +
                    "         provided with the distribution.\n" +
                    "\n" +
                    "THIS SOFTWARE IS PROVIDED BY GOOGLE, INC. ``AS IS'' AND ANY EXPRESS OR IMPLIED\n" +
                    "WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND\n" +
                    "FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GOOGLE, INC. OR\n" +
                    "CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\n" +
                    "CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR\n" +
                    "SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON\n" +
                    "ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING\n" +
                    "NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF\n" +
                    "ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n" +
                    "\n" +
                    "The views and conclusions contained in the software and documentation are those of the\n" +
                    "authors and should not be interpreted as representing official policies, either expressed\n" +
                    "or implied, of Google, Inc.\n" +
                    "---------------------------------------------------------------------------------------------\n" +
                    "License for third_party/disklrucache:\n" +
                    "\n" +
                    "Copyright 2012 Jake Wharton\n" +
                    "Copyright 2011 The Android Open Source Project\n" +
                    "\n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    "you may not use this file except in compliance with the License.\n" +
                    "You may obtain a copy of the License at\n" +
                    "\n" +
                    "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                    "\n" +
                    "Unless required by applicable law or agreed to in writing, software\n" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "See the License for the specific language governing permissions and\n" +
                    "limitations under the License.\n" +
                    "---------------------------------------------------------------------------------------------\n" +
                    "License for third_party/gif_decoder:\n" +
                    "\n" +
                    "Copyright (c) 2013 Xcellent Creations, Inc.\n" +
                    "\n" +
                    "Permission is hereby granted, free of charge, to any person obtaining\n" +
                    "a copy of this software and associated documentation files (the\n" +
                    "\"Software\"), to deal in the Software without restriction, including\n" +
                    "without limitation the rights to use, copy, modify, merge, publish,\n" +
                    "distribute, sublicense, and/or sell copies of the Software, and to\n" +
                    "permit persons to whom the Software is furnished to do so, subject to\n" +
                    "the following conditions:\n" +
                    "\n" +
                    "The above copyright notice and this permission notice shall be\n" +
                    "included in all copies or substantial portions of the Software.\n" +
                    "\n" +
                    "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND,\n" +
                    "EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF\n" +
                    "MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND\n" +
                    "NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE\n" +
                    "LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION\n" +
                    "OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION\n" +
                    "WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.\n" +
                    "---------------------------------------------------------------------------------------------\n" +
                    "License for third_party/gif_encoder/AnimatedGifEncoder.java and\n" +
                    "third_party/gif_encoder/LZWEncoder.java:\n" +
                    "\n" +
                    "No copyright asserted on the source code of this class. May be used for any\n" +
                    "purpose, however, refer to the Unisys LZW patent for restrictions on use of\n" +
                    "the associated LZWEncoder class. Please forward any corrections to\n" +
                    "kweiner@fmsware.com.\n" +
                    "\n" +
                    "-----------------------------------------------------------------------------\n" +
                    "License for third_party/gif_encoder/NeuQuant.java\n" +
                    "\n" +
                    "Copyright (c) 1994 Anthony Dekker\n" +
                    "\n" +
                    "NEUQUANT Neural-Net quantization algorithm by Anthony Dekker, 1994. See\n" +
                    "\"Kohonen neural networks for optimal colour quantization\" in \"Network:\n" +
                    "Computation in Neural Systems\" Vol. 5 (1994) pp 351-367. for a discussion of\n" +
                    "the algorithm.\n" +
                    "\n" +
                    "Any party obtaining a copy of these files from the author, directly or\n" +
                    "indirectly, is granted, free of charge, a full and unrestricted irrevocable,\n" +
                    "world-wide, paid up, royalty-free, nonexclusive right and license to deal in\n" +
                    "this software and documentation files (the \"Software\"), including without\n" +
                    "limitation the rights to use, copy, modify, merge, publish, distribute,\n" +
                    "sublicense, and/or sell copies of the Software, and to permit persons who\n" +
                    "receive copies from any such party to do so, with the only requirement being\n" +
                    "that this copyright notice remain intact.";

            public static final String butterknife = "Copyright 2013 Jake Wharton\n" +
                    "\n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    "you may not use this file except in compliance with the License.\n" +
                    "You may obtain a copy of the License at\n" +
                    "\n" +
                    "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                    "\n" +
                    "Unless required by applicable law or agreed to in writing, software\n" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "See the License for the specific language governing permissions and\n" +
                    "limitations under the License.";

            public static final String expandabletextview = "Copyright 2016 Cliff Ophalvens (Blogc.at)\n" +
                    "\n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    "you may not use this file except in compliance with the License.\n" +
                    "You may obtain a copy of the License at\n" +
                    "\n" +
                    "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                    "\n" +
                    "Unless required by applicable law or agreed to in writing, software\n" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "See the License for the specific language governing permissions and\n" +
                    "limitations under the License.";

            public static final String materialdialog = "The MIT License (MIT)\n" +
                    "\n" +
                    "Copyright (c) 2014-2016 Aidan Michael Follestad\n" +
                    "\n" +
                    "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                    "of this software and associated documentation files (the \"Software\"), to deal\n" +
                    "in the Software without restriction, including without limitation the rights\n" +
                    "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                    "copies of the Software, and to permit persons to whom the Software is\n" +
                    "furnished to do so, subject to the following conditions:\n" +
                    "\n" +
                    "The above copyright notice and this permission notice shall be included in all\n" +
                    "copies or substantial portions of the Software.\n" +
                    "\n" +
                    "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                    "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                    "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                    "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                    "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                    "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                    "SOFTWARE.";

            public static final String realm = "This software contains components with separate copyright and license terms.\n" +
                    "Your use of these components is subject to the terms and conditions of the\n" +
                    "following licenses.\n" +
                    "\n" +
                    "For the Realm Platform Extensions component\n" +
                    "\n" +
                    "  Realm Platform Extensions License\n" +
                    "\n" +
                    "  Copyright (c) 2011-2017 Realm Inc All rights reserved\n" +
                    "\n" +
                    "  Redistribution and use in binary form, with or without modification, is\n" +
                    "  permitted provided that the following conditions are met:\n" +
                    "\n" +
                    "  1. You agree not to attempt to decompile, disassemble, reverse engineer or\n" +
                    "  otherwise discover the source code from which the binary code was derived.\n" +
                    "  You may, however, access and obtain a separate license for most of the\n" +
                    "  source code from which this Software was created, at\n" +
                    "  http://realm.io/pricing/.\n" +
                    "\n" +
                    "  2. Redistributions in binary form must reproduce the above copyright notice,\n" +
                    "  this list of conditions and the following disclaimer in the documentation\n" +
                    "  and/or other materials provided with the distribution.\n" +
                    "\n" +
                    "  3. Neither the name of the copyright holder nor the names of its\n" +
                    "  contributors may be used to endorse or promote products derived from this\n" +
                    "  software without specific prior written permission.\n" +
                    "\n" +
                    "  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"\n" +
                    "  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE\n" +
                    "  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE\n" +
                    "  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE\n" +
                    "  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\n" +
                    "  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF\n" +
                    "  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS\n" +
                    "  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN\n" +
                    "  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\n" +
                    "  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE\n" +
                    "POSSIBILITY OF SUCH DAMAGE.";

            public static final String flaticon = "Thankyou for all author Flaticon";
        }
    }
}
