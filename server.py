#!/usr/bin/env python3
"""
Simple HTTP server for Student Record System with real-time CSV sync
Serves HTML and handles CSV read/write operations
"""

import http.server
import socketserver
import json
import csv
import os
from pathlib import Path
from urllib.parse import urlparse, parse_qs

PORT = 8000
CSV_PATH = Path(__file__).parent / "JavaSwing" / "MOCK_DATA.csv"
HTML_DIR = Path(__file__).parent / "JavaScript"

class CSVHandler(http.server.SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=str(HTML_DIR), **kwargs)
    
    def do_GET(self):
        parsed_path = urlparse(self.path)
        
        # API endpoint to get CSV data as JSON
        if parsed_path.path == '/api/students':
            try:
                students = []
                with open(CSV_PATH, 'r', newline='', encoding='utf-8') as f:
                    reader = csv.DictReader(f)
                    if reader.fieldnames:
                        for row in reader:
                            students.append(row)
                
                self.send_response(200)
                self.send_header('Content-type', 'application/json')
                self.send_header('Access-Control-Allow-Origin', '*')
                self.end_headers()
                self.wfile.write(json.dumps(students).encode())
            except Exception as e:
                self.send_response(500)
                self.send_header('Content-type', 'application/json')
                self.send_header('Access-Control-Allow-Origin', '*')
                self.end_headers()
                self.wfile.write(json.dumps({'error': str(e)}).encode())
        else:
            super().do_GET()
    
    def do_POST(self):
        parsed_path = urlparse(self.path)
        
        # API endpoint to save CSV data from JSON
        if parsed_path.path == '/api/students':
            try:
                content_length = int(self.headers.get('Content-Length', 0))
                body = self.rfile.read(content_length)
                students = json.loads(body.decode())
                
                # Write to CSV
                with open(CSV_PATH, 'w', newline='', encoding='utf-8') as f:
                    fieldnames = ['StudentID', 'first_name', 'last_name', 'LAB WORK 1', 'LAB WORK 2', 'LAB WORK 3', 'PRELIM EXAM', 'ATTENDANCE GRADE']
                    writer = csv.DictWriter(f, fieldnames=fieldnames)
                    writer.writeheader()
                    for student in students:
                        writer.writerow({
                            'StudentID': student.get('id', ''),
                            'first_name': student.get('firstName', ''),
                            'last_name': student.get('lastName', ''),
                            'LAB WORK 1': student.get('lab1', ''),
                            'LAB WORK 2': student.get('lab2', ''),
                            'LAB WORK 3': student.get('lab3', ''),
                            'PRELIM EXAM': student.get('prelim', ''),
                            'ATTENDANCE GRADE': student.get('attendance', '')
                        })
                
                self.send_response(200)
                self.send_header('Content-type', 'application/json')
                self.send_header('Access-Control-Allow-Origin', '*')
                self.end_headers()
                self.wfile.write(json.dumps({'success': True}).encode())
            except Exception as e:
                self.send_response(500)
                self.send_header('Content-type', 'application/json')
                self.send_header('Access-Control-Allow-Origin', '*')
                self.end_headers()
                self.wfile.write(json.dumps({'error': str(e)}).encode())
        else:
            self.send_response(404)
            self.send_header('Content-type', 'application/json')
            self.send_header('Access-Control-Allow-Origin', '*')
            self.end_headers()
            self.wfile.write(json.dumps({'error': 'Not found'}).encode())
    
    def do_OPTIONS(self):
        self.send_response(200)
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS')
        self.send_header('Access-Control-Allow-Headers', 'Content-type')
        self.end_headers()
    
    def log_message(self, format, *args):
        print(f"[{self.log_date_time_string()}] {format%args}")

if __name__ == '__main__':
    with socketserver.TCPServer(("", PORT), CSVHandler) as httpd:
        print(f"Server running at http://localhost:{PORT}")
        print(f"Open http://localhost:{PORT}/StudentRecordSystem.html in your browser")
        print(f"CSV file: {CSV_PATH}")
        print("Press Ctrl+C to stop the server\n")
        try:
            httpd.serve_forever()
        except KeyboardInterrupt:
            print("\nServer stopped.")
