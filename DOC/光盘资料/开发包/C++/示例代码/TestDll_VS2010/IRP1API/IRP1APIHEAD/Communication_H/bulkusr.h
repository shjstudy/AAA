 /*******************************************************************************
 *  INTEL CONFIDENTIAL
 *  Copyright 2006 Intel Corporation All Rights Reserved.
 *
 *  The source code contained or described herein and all documents related to
 *  the source code ("Material") are owned by Intel Corporation or its suppliers
 *  or licensors. Title to the Material remains with Intel Corporation or its
 *  suppliers and licensors. The Material may contain trade secrets and
 *  proprietary and confidential information of Intel Corporation and its
 *  suppliers and licensors, and is protected by worldwide copyright and trade
 *  secret laws and treaty provisions. No part of the Material may be used,
 *  copied, reproduced, modified, published, uploaded, posted, transmitted,
 *  distributed, or disclosed in any way without Intel's prior express written
 *  permission. 
 *  
 *  No license under any patent, copyright, trade secret or other intellectual
 *  property right is granted to or conferred upon you by disclosure or delivery
 *  of the Materials, either expressly, by implication, inducement, estoppel or
 *  otherwise. Any license under such intellectual property rights must be
 *  express and approved by Intel in writing.
 *
 *  Unless otherwise agreed by Intel in writing, you may not remove or alter
 *  this notice or any other notice embedded in Materials by Intel or Intel's
 *  suppliers or licensors in any way.
 ******************************************************************************/

/******************************************************************************
 *
 * Name: bulkusr.h
 * 
 *  Description:
 *       Trasnport Library IOCTl defintions for Win32 XP
 *  
 ******************************************************************************/


/*++

Copyright (c) 2000  Microsoft Corporation

Module Name:

    sSUsr.h

Abstract:

Environment:

    Kernel mode

Notes:

    Copyright (c) 2000 Microsoft Corporation.  
    All Rights Reserved.

--*/

#ifndef _BULKUSB_USER_H
#define _BULKUSB_USER_H

#include <initguid.h>

// {48C602D4-C77E-45b9-8133-20C9683BD1A6}
DEFINE_GUID(GUID_CLASS_HARVEMAC, 
0x48c602d4, 0xc77e, 0x45b9, 0x81, 0x33, 0x20, 0xc9, 0x68, 0x3b, 0xd1, 0xa6);

#define HARVEMAC_IOCTL_INDEX             0x0000


#define IOCTL_HARVEMAC_WRITE                  CTL_CODE(FILE_DEVICE_UNKNOWN,     \
                                                    HARVEMAC_IOCTL_INDEX,     \
                                                     METHOD_BUFFERED,         \
                                                     FILE_ANY_ACCESS)
                                                   
#define IOCTL_HARVEMAC_READ                  CTL_CODE(FILE_DEVICE_UNKNOWN,     \
                                                     HARVEMAC_IOCTL_INDEX + 1, \
                                                     METHOD_BUFFERED,         \
                                                     FILE_ANY_ACCESS)

#define IOCTL_HARVEMAC_READ_CNT              CTL_CODE(FILE_DEVICE_UNKNOWN,     \
                                                     HARVEMAC_IOCTL_INDEX + 2, \
                                                     METHOD_BUFFERED,         \
                                                     FILE_ANY_ACCESS)

#define IOCTL_HARVEMAC_CANCEL                CTL_CODE(FILE_DEVICE_UNKNOWN,     \
                                                     HARVEMAC_IOCTL_INDEX + 3, \
                                                     METHOD_BUFFERED,         \
                                                     FILE_ANY_ACCESS)

#define IOCTL_HARVEMAC_SOFTRESET             CTL_CODE(FILE_DEVICE_UNKNOWN,     \
                                                     HARVEMAC_IOCTL_INDEX + 4, \
                                                     METHOD_BUFFERED,         \
                                                     FILE_ANY_ACCESS)

#define IOCTL_HARVEMAC_ABORT                 CTL_CODE(FILE_DEVICE_UNKNOWN,     \
                                                     HARVEMAC_IOCTL_INDEX + 5, \
                                                     METHOD_BUFFERED,         \
                                                     FILE_ANY_ACCESS)

#endif

