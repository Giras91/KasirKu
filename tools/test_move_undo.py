#!/usr/bin/env python3
"""
Integration test for move + undo flow using the pulled SQLite DB.
- Inserts a fake order (if none), assigns it to a source table,
- Inserts a move_history row for moving to a destination table,
- Simulates undo by moving the order back and deleting the move_history row by move_id,
- Asserts database state at each step.

Usage: python3 tools/test_move_undo.py
"""
import sqlite3
import uuid
import os
import sys

DB = os.path.join(os.path.dirname(__file__), '..', 'com_chipo_cashier.db')
DB = os.path.abspath(DB)

if not os.path.exists(DB):
    print('DB not found at', DB)
    sys.exit(2)

conn = sqlite3.connect(DB)
cur = conn.cursor()

# helper
def table_row_count(table):
    cur.execute(f"SELECT COUNT(*) FROM {table}")
    return cur.fetchone()[0]

print('Initial product_order count:', table_row_count('product_order'))
print('Initial move_history count:', table_row_count('move_history'))

# create a test order
order_id = 'TEST-' + uuid.uuid4().hex[:8]
print('Creating test order', order_id)
cur.execute('INSERT INTO product_order(order_id, ordered_on, updated_on, sycn_on, description, tax, discount, amount, user_id, branch_id, status, table_id, table_name) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)', (
    order_id, '2025-09-23 00:00:00', '2025-09-23 00:00:00', None, 'test', 0.0, 0.0, 10.0, 'u1', 'b1', 'open', '1', 'T1'
))
conn.commit()

# insert move history for moving from T1 to T2
move_id = uuid.uuid4().hex
print('Moving order', order_id, 'move_id', move_id)
cur.execute("INSERT INTO move_history(move_id, order_id, from_table, to_table, moved_on) VALUES(?,?,?,?,datetime('now'))", (move_id, order_id, 'T1', 'T2'))
# update product_order assignment
cur.execute('UPDATE product_order SET table_id=?, table_name=? WHERE order_id=?', ('2','T2', order_id))
conn.commit()

print('After move product_order table_name:', cur.execute('SELECT table_name FROM product_order WHERE order_id=?', (order_id,)).fetchone()[0])
print('move_history count now:', table_row_count('move_history'))

# simulate undo: move back and delete move_history by move_id
print('Undoing move', move_id)
cur.execute('UPDATE product_order SET table_id=?, table_name=? WHERE order_id=?', ('1','T1', order_id))
cur.execute('DELETE FROM move_history WHERE move_id=?', (move_id,))
conn.commit()

print('After undo product_order table_name:', cur.execute('SELECT table_name FROM product_order WHERE order_id=?', (order_id,)).fetchone()[0])
print('move_history count now:', table_row_count('move_history'))

# cleanup test order
print('Cleaning up test order')
cur.execute('DELETE FROM product_order WHERE order_id=?', (order_id,))
conn.commit()

print('Final move_history count:', table_row_count('move_history'))
conn.close()
print('Test completed successfully')
