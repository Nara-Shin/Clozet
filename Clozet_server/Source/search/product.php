<?
	include "/www/clozet/lib/dbconnect.php";
	
	/*
		��ǰ�˻�
		���ڵ�� ��ǰ �˻�
		2016.01.06 by Junseok

		1. Request
		barcode=�ִ� 20�ڸ����ڿ�
		member_code=6�ڸ� ȸ���ڵ�

		2. Response
		{
		"product_code":6�ڸ� �ڵ�
		"product_brand":�귣���
		"product_name":��ǰ��
		"product_detail":��ǰ����
		"product_price":��ǰ����
		"product_image":��ǰ�̹��� URL
		"product_sizes":��ǰ������(,�� ����)
		"product_colors":��ǰ����(,�� ����)
		"options"[{
			   "size":��ǰ������
			   "color":��ǰ����
			   "stock":����
			   "code":6�ڸ� �ɼ��ڵ�
		},]
		}
	*/

	// Query 1
	$query = " select 'complete' as col from dual ";

	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)) 
	{
	   echo $row['col'].'<br/>';
	}

?>
